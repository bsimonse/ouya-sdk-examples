/*
 * Copyright (C) 2012, 2013 OUYA, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tv.ouya.examples.android.multipleactivitieswebview;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebSettings.PluginState;
import android.widget.Button;
import android.os.Bundle;


public class Activity2 extends Activity
{
	Button m_btnActivity = null;
	
	WebView m_WebView = null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.layout_activity2);
        
        m_btnActivity = (Button)findViewById(R.id.btnActivity);        
        
        m_btnActivity.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent openIntent = new Intent("tv.ouya.examples.android.multipleactivitieswebview.ACTION1");
		        startActivity(openIntent);
			}
		});
        
        m_WebView = (WebView)findViewById(R.id.wvContent);
    }
    
    @Override
    public void onResume()
    {
    	super.onResume();
    	
		if (null != m_WebView.getSettings())
		{
			m_WebView.getSettings().setJavaScriptEnabled(true);
			m_WebView.getSettings().setPluginState(PluginState.ON);
		}
    	
		m_WebView.setWebChromeClient(new WebChromeClient() {
			public void onPageFinished(WebView view, String url) {
				Log.i("MultipleActivitiesWebView:", "onPageFinished="+url);
		    }
		});
		
		m_WebView.clearCache(true);
        
        m_WebView.loadUrl("http://devs.ouya.tv/developers");        
    }
    
    @Override
    public void onPause()
    {
    	super.onPause();
    	
    	//activity instance is destroyed, so BUTTON_A will exit
    	finish();
    }
}