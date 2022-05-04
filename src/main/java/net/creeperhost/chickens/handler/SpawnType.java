package net.creeperhost.chickens.handler;

public enum SpawnType
{
    NORMAL, SNOW, NONE, HELL;

    public static String[] names()
    {
        SpawnType[] states = values();
        String[] names = new String[states.length];

        for (int i = 0; i < states.length; i++)
        {
            names[i] = states[i].name();
        }

        return names;
    }
}
