package com.mestaoui.rvproject.service;

import com.mestaoui.rvproject.beans.Actor;
import com.mestaoui.rvproject.dao.IDao;

import java.util.ArrayList;
import java.util.List;

public class ActorService implements IDao<Actor> {
    private List<Actor> actors;
    private static ActorService instance;

    public ActorService() {
        this.actors = new ArrayList<>();
    }

    public static ActorService getInstance() {
        if(instance == null)
            instance = new ActorService();
        return instance;
    }

    @Override
    public boolean create(Actor o) {
        return actors.add(o);
    }

    @Override
    public boolean update(Actor o) {
        for(Actor a : actors) {
            if(a.getId() == o.getId()) {
                int index = actors.indexOf(a);
                actors.remove(a);
                actors.add(index, o);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(Actor o) {
        return actors.remove(o);
    }

    @Override
    public Actor findById(int id) {
        for(Actor a : actors) {
            if(a.getId() == id) {
                return a;
            }
        }
        return null;
    }

    @Override
    public List<Actor> findAll() {
        return actors;
    }
}
