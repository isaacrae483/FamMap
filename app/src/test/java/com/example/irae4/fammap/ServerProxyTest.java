package com.example.irae4.fammap;

import com.google.gson.Gson;

import org.junit.*;


import java.util.HashSet;
import java.util.Set;


import Model.*;
import Model.LoginData;
import Responce.*;
import Responce.Error;
import ServerAccess.ServerProxy;


import static org.junit.Assert.assertEquals;

public class ServerProxyTest {


    LoginData loginData = new LoginData();
    ServerProxy serverProxy = new ServerProxy();
    public ServerProxyTest(){
        loginData.setHost("192.168.1.6");
        loginData.setPort("8080");
        loginData.setFirstName("Isaac");
        loginData.setLastName("Rae");
        loginData.setEmail("email@gmail");
        loginData.setGender("m");
    }

    @Test
    public void registerTest(){
        loginData.setUserName("username");
        loginData.setPassword("password");
        Response response = serverProxy.register(loginData);
        RegisterResponse test = new RegisterResponse(null, null, null);
        assertEquals(test.getClass(), response.getClass());

        response = serverProxy.register(loginData);
        Error test1 = new Error(null);
        assertEquals(test1.getClass(), response.getClass());
    }

    @Test
    public void loginTest(){
        loginData.setUserName("turtle");
        loginData.setPassword("pineapple");
        serverProxy.register(loginData);

        Response response = serverProxy.login(loginData);
        LoginResponse test = new LoginResponse(null, null, null);
        assertEquals(test.getClass(), response.getClass());

        loginData.setUserName("humbug");
        response = serverProxy.login(loginData);
        Error test1 = new Error(null);
        assertEquals(test1.getClass(), response.getClass());
    }

    @Test
    public void getPersonTest(){
        loginData.setUserName("uDaMan");
        loginData.setPassword("DaWord");
        Response response = serverProxy.register(loginData);
        RegisterResponse resp = (RegisterResponse)response;
        Response peopleResponse = serverProxy.getPeople(resp.getAuthToken(), loginData.getHost(), loginData.getPort());
        Set<Person> set = new HashSet<>();
        set.add(new Person(null, null, null, null, null));
        PersonResponse test = new PersonResponse(set);
        assertEquals(test.getClass(), peopleResponse.getClass());

        peopleResponse = serverProxy.getPeople("hello", loginData.getHost(), loginData.getPort());
        Error test1 = new Error(null);
        assertEquals(test1.getClass(), peopleResponse.getClass());


    }

    @Test
    public void getEventTest(){
        loginData.setUserName("uMyWoMan");
        loginData.setPassword("WowGirls");
        Response response = serverProxy.register(loginData);
        RegisterResponse resp = (RegisterResponse)response;
        Response eventResponse = serverProxy.getEvents(resp.getAuthToken(), loginData.getHost(), loginData.getPort());
        Set<Event> set = new HashSet<>();
        set.add(new Event(null, null, null, null, null, null, null, null, 0));
        EventResponse test = new EventResponse(set);
        assertEquals(test.getClass(), eventResponse.getClass());

        eventResponse = serverProxy.getPeople("world", loginData.getHost(), loginData.getPort());
        Error test1 = new Error(null);
        assertEquals(test1.getClass(), eventResponse.getClass());
    }

}
