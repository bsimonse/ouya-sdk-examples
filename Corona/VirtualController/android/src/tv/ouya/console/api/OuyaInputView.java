package tv.ouya.console.api;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.ansca.corona.input.CoronaKeyEvent;

import tv.ouya.console.api.*;
import tv.ouya.sdk.corona.CallbacksOuyaInput;
import tv.ouya.sdk.corona.IOuyaActivity;

public class OuyaInputView extends View {
	
	private static final String TAG = OuyaInputView.class.getSimpleName();
	private Activity mActivity = null;

	public OuyaInputView(Context context) {
		super(context);
		//Log.i(TAG, "OuyaInputView(Context)");
		
		mActivity = com.ansca.corona.CoronaEnvironment.getCoronaActivity();
		OuyaInputMapper.init(mActivity);
		
		this.setFocusable(true);
	}
	
	/**
	 * Dispatch KeyEvents to the OuyaInputMapper. The remapped input will
	 * return to the activity with onGenericMotionEvent, onKeyDown, and onKeyUp
	 * events.
	 *
	 * Be sure to use the remapped input from the above events instead of
	 * the KeyEvent passed to this method that has not been remapped yet.
	 *
	 * @param keyEvent passed passed to the OuyaInputMapper
	 */
	@Override
	public boolean dispatchKeyEvent(KeyEvent keyEvent) {
		if (keyEvent instanceof CoronaKeyEvent) {
			//eat the extra event
			return true;
		}
        if (OuyaInputMapper.shouldHandleInputEvent(keyEvent)) {
		    return OuyaInputMapper.dispatchKeyEvent(mActivity, keyEvent);
        } else {
            return super.dispatchKeyEvent(keyEvent);
        }
	}

	/**
	 * Dispatch MotionEvents to the OuyaInputMapper. The remapped input will
	 * return to the activity with onGenericMotionEvent, onKeyDown, and onKeyUp
	 * events.
	 *
	 * Be sure to use the remapped input from the above events instead of
	 * the MotionEvent passed to this method that has not been remapped yet.
	 *
	 * @param motionEvent passed passed to the OuyaInputMapper
	 */
	@Override
	public boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
        if (OuyaInputMapper.shouldHandleInputEvent(motionEvent)) {
		    return OuyaInputMapper.dispatchGenericMotionEvent(mActivity, motionEvent);
        } else {
            return super.dispatchGenericMotionEvent(motionEvent);
        }
    }

	@Override
	public boolean onGenericMotionEvent(MotionEvent motionEvent) {
		//DebugInput.debugMotionEvent(motionEvent);

		int playerNum = OuyaController.getPlayerNumByDeviceId(motionEvent.getDeviceId());	    
	    if (playerNum < 0) {
	    	Log.e(TAG, "Failed to find playerId for Controller="+motionEvent.getDevice().getName());
	    	return true;
	    }
	    
	    CallbacksOuyaInput input = IOuyaActivity.GetCallbacksOuyaInput();
	    if (null != input) {
	    	
	    	input.onGenericMotionEvent(playerNum, OuyaController.AXIS_LS_X, motionEvent.getAxisValue(OuyaController.AXIS_LS_X));
	    	input.onGenericMotionEvent(playerNum, OuyaController.AXIS_LS_Y, motionEvent.getAxisValue(OuyaController.AXIS_LS_Y));
	    	input.onGenericMotionEvent(playerNum, OuyaController.AXIS_RS_X, motionEvent.getAxisValue(OuyaController.AXIS_RS_X));
	    	input.onGenericMotionEvent(playerNum, OuyaController.AXIS_RS_Y, motionEvent.getAxisValue(OuyaController.AXIS_RS_Y));
	    	input.onGenericMotionEvent(playerNum, OuyaController.AXIS_L2, motionEvent.getAxisValue(OuyaController.AXIS_L2));
	    	input.onGenericMotionEvent(playerNum, OuyaController.AXIS_R2, motionEvent.getAxisValue(OuyaController.AXIS_R2));
	    }
	    
		return true;
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent keyEvent) {
		//Log.i(TAG, "onKeyUp keyCode=" + DebugInput.debugGetButtonName(keyCode));

		int playerNum = OuyaController.getPlayerNumByDeviceId(keyEvent.getDeviceId());	    
	    if (playerNum < 0) {
	    	Log.e(TAG, "Failed to find playerId for Controller="+keyEvent.getDevice().getName());
	    	return true;
	    }

		int action = keyEvent.getAction();
		
		CallbacksOuyaInput input = IOuyaActivity.GetCallbacksOuyaInput();
	    if (null != input) {	
	    	input.onKeyUp(playerNum, keyCode);
	    }
		
		return true;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent keyEvent) {
		//Log.i(TAG, "onKeyDown keyCode=" + DebugInput.debugGetButtonName(keyCode));

		int playerNum = OuyaController.getPlayerNumByDeviceId(keyEvent.getDeviceId());	    
	    if (playerNum < 0) {
	    	Log.e(TAG, "Failed to find playerId for Controller="+keyEvent.getDevice().getName());
	    	return true;
	    }

		int action = keyEvent.getAction();
		
		CallbacksOuyaInput input = IOuyaActivity.GetCallbacksOuyaInput();
	    if (null != input) {	
	    	input.onKeyDown(playerNum, keyCode);
	    }
		
		return true;
	}
}