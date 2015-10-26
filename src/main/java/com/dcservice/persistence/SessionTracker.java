package com.dcservice.persistence;


public class SessionTracker 
{
    private static SessionTracker instance;

    public static synchronized SessionTracker getInstance()
    {
        if (instance == null)
        {
            instance = new SessionTracker();
        }

        return instance;
    }

    public void sessionOpening()
    {
        this.sessionOpening(null);
    }

    public void sessionClosing()
    {
        this.sessionClosing(null);
    }

    public void sessionOpening(String clazz)
    {
    }

    public void sessionClosing(String clazz)
    {
    }
}
