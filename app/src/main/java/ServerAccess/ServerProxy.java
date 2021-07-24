package ServerAccess;

import java.io.*;
import java.net.*;

import com.google.gson.*;

import Model.LoginData;
import Requests.LoginRequest;
import Requests.RegisterRequest;
import Responce.EventResponse;
import Responce.LoginResponse;
import Responce.RegisterResponse;
import Responce.PersonResponse;
import Responce.Response;
import Responce.Error;

public class ServerProxy {

//login to server
    public Response login(LoginData data){
        LoginRequest loginRequest = new LoginRequest(data.getUserName(), data.getPassword());
        Gson gson;
        try{
            URL url = new URL ("http://" + data.getHost() + ":" + data.getPort() + "/user/login");//sets url

            gson = new GsonBuilder().setPrettyPrinting().create();//makes request body
            String reqData = gson.toJson(loginRequest);

            HttpURLConnection http = (HttpURLConnection)url.openConnection();//set GET/POST
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.connect();//sends request


            OutputStreamWriter reqBody = new OutputStreamWriter(http.getOutputStream());
            reqBody.write(reqData);
            reqBody.flush();
            reqBody.close();

            //checking response;
            if(http.getResponseCode() == HttpURLConnection.HTTP_OK){//checks if login successful
                InputStreamReader respBody = new InputStreamReader(http.getInputStream());
                gson = new Gson();
                LoginResponse response = gson.fromJson(respBody, LoginResponse.class);
                respBody.close();
                http.disconnect();
                return response;
            }
            else{
                return new Error("Invalid Username and/or Password");
            }

        }catch(IOException e) {
            e.printStackTrace();
        }

        return new Error("Nothing worked");
    }
    public Response register(LoginData data){//registers with server
        RegisterRequest registerRequest = new RegisterRequest(data.getUserName(), data.getPassword(), data.getEmail(), data.getFirstName(), data.getLastName(), data.getGender());
        Gson gson;
        try{
            URL url = new URL ("http://" + data.getHost() + ":" + data.getPort() + "/user/register");//makes url

            gson = new GsonBuilder().setPrettyPrinting().create();//makes request body
            String reqData = gson.toJson(registerRequest);
            System.out.println("\n\n\n" + reqData + "\n\n\n");

            HttpURLConnection http = (HttpURLConnection)url.openConnection();//sets GET/POST
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.connect();//sends request


            OutputStreamWriter reqBody = new OutputStreamWriter(http.getOutputStream());
            reqBody.write(reqData);
            reqBody.flush();
            reqBody.close();

            //checking response;
            if(http.getResponseCode() == HttpURLConnection.HTTP_OK){//checks if register successful

                InputStreamReader respBody = new InputStreamReader(http.getInputStream());
                gson = new Gson();
                RegisterResponse response = gson.fromJson(respBody, RegisterResponse.class);
                respBody.close();
                http.disconnect();
                return response;
            }
            else{
                return new Error("Could not register, Please choose a different username and try again");
            }

        }catch(IOException e) {
            e.printStackTrace();
        }


        return new Error("Nothing worked");
    }
    public Response getPeople(String token, String host, String port){//get people for the given user
        Gson gson;
        try{
            URL url = new URL ("http://" + host + ":" + port + "/person");//set url

            HttpURLConnection http = (HttpURLConnection)url.openConnection();//set GET/POST
            http.setRequestMethod("GET");
            http.setDoOutput(false);
            http.addRequestProperty("Authorization", token);
            http.connect();//send request


            //checking response;
            if(http.getResponseCode() == HttpURLConnection.HTTP_OK){//check if request returned correctly
                InputStreamReader respBody = new InputStreamReader(http.getInputStream());
                gson = new Gson();
                PersonResponse response = gson.fromJson(respBody, PersonResponse.class);
                respBody.close();
                http.disconnect();
                return response;
            }
            else{
                return new Error("Could not find ancestors, please try again");
            }

        }catch(IOException e) {
            e.printStackTrace();
        }

        return new Error("Nothing worked");
    }
    public Response getEvents(String token, String host, String port){//get list of events for user
        Gson gson;
        try{
            URL url = new URL ("http://" + host + ":" + port + "/event");//set URL

            HttpURLConnection http = (HttpURLConnection)url.openConnection();//set GET/POST
            http.setRequestMethod("GET");
            http.setDoOutput(false);
            http.addRequestProperty("Authorization", token);
            http.connect();


            //checking response;
            if(http.getResponseCode() == HttpURLConnection.HTTP_OK){//check if request returned correctly
                InputStreamReader respBody = new InputStreamReader(http.getInputStream());
                gson = new Gson();
                EventResponse response = gson.fromJson(respBody, EventResponse.class);
                respBody.close();
                http.disconnect();
                return response;
            }
            else{
                return new Error("Could not find events, please try again");
            }

        }catch(IOException e) {
            e.printStackTrace();
        }

        return new Error("Nothing worked");
    }
}
