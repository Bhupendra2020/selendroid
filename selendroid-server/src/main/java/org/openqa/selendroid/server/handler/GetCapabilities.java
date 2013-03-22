/*
 * Copyright 2012 selendroid committers.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.openqa.selendroid.server.handler;

import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selendroid.server.RequestHandler;
import org.openqa.selendroid.server.Response;
import org.openqa.selendroid.server.Session;
import org.openqa.selendroid.util.SelendroidLogger;
import org.webbitserver.HttpRequest;

import android.os.Build;

public class GetCapabilities extends RequestHandler {
  String BROWSER_NAME = "browserName";
  String PLATFORM = "platform";
  String SUPPORTS_JAVASCRIPT = "javascriptEnabled";
  String TAKES_SCREENSHOT = "takesScreenshot";
  String VERSION = "version";
  String SUPPORTS_ALERTS = "handlesAlerts";
  String ROTATABLE = "rotatable";
  String ACCEPT_SSL_CERTS = "acceptSslCerts";

  public GetCapabilities(HttpRequest request,String mappedUri) {
    super(request,mappedUri);
  }

  @Override
  public Response handle() throws JSONException {
    SelendroidLogger.log("get capabilities command");
    Session session = getSelendroidDriver().getSession();

    JSONObject caps = getSelendroidDriver().getSessionCapabilities(session.getSessionId());
    JSONObject copy = new JSONObject(caps,caps.names().join(",").split(","));
    
    copy.put(TAKES_SCREENSHOT, true);
    copy.put(BROWSER_NAME, "selendroid");
    copy.put(ROTATABLE, false);
    copy.put(PLATFORM, "android");
    copy.put(SUPPORTS_ALERTS, true);
    copy.put(SUPPORTS_JAVASCRIPT, true);
    copy.put(VERSION, Build.VERSION.SDK);
    copy.put(ACCEPT_SSL_CERTS, true);
    return new Response(session.getSessionId(), copy);
  }
}