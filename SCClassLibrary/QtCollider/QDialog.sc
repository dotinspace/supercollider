QFileDialog : QObject {
  *new { arg okFunc, cancelFunc, fileMode, acceptMode, stripResult = false;

    var me = super.new("QcFileDialog", [fileMode, acceptMode] );

    if( okFunc.notNil ) {
      me.connectFunction( 'accepted(VariantList)', {
        |me, result|
        if( stripResult && (fileMode != 3) ) { result = result[0]; };
        okFunc.value(result)
      });
    };

    if( cancelFunc.notNil ) {
      me.connectFunction( 'rejected()', { cancelFunc.value() } );
    };

    me.invokeMethod('show', synchronous:false);

    ^me;
  }
}

QDialog {
  *getPaths { arg okFunc, cancelFunc, allowsMultiple=true;
    var fileMode;
    if( allowsMultiple ) { fileMode = 3 } { fileMode = 1 };
    ^QFileDialog.new( okFunc, cancelFunc, fileMode, 0 );
  }

  *openPanel { arg okFunc, cancelFunc, multipleSelection=false;
    var fileMode;
    if( multipleSelection ) { fileMode = 3 } { fileMode = 1 };
    ^QFileDialog.new( okFunc, cancelFunc, fileMode, 0, stripResult:true );
  }

  *savePanel { arg okFunc, cancelFunc;
    ^QFileDialog.new( okFunc, cancelFunc, 0, 1 );
  }
}
