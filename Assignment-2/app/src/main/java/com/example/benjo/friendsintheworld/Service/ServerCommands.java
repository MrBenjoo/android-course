package com.example.benjo.friendsintheworld.Service;


import org.json.JSONException;
import org.json.JSONObject;


public class ServerCommands {

    public String registration(String name, String group) {
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("type", "register");
            jsonObject.put("group", group);
            jsonObject.put("member", name);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }


    public String deregistration(String id) {
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("type", "unregister");
            jsonObject.put("id", id);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }


    public String membersInAGroup(String name) {
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("type", "members");
            jsonObject.put("group", name);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public String currentGroups() {
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("type", "groups");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public String setPosition(String id, String longitude, String latitude) {
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("type", "location");
            jsonObject.put("id", id);
            jsonObject.put("longitude", longitude);
            jsonObject.put("latitude", latitude);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

}
