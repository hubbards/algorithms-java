package com.github.hubbards.algorithms.schedule;

public class Request {
    private String name;
    private float start;
    private float finish;

    public Request(String name, float start, float finish) {
        this.name = name;
        this.start = start;
        this.finish = finish;
    }

    public String getName() {
        return name;
    }

    public float getStart() {
        return start;
    }

    public float getFinish() {
        return finish;
    }

    @Override
    public String toString() {
        return String.format("(name: %s, start: %2f, finish: %2f)", name, start, finish);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Request) {
            Request other = (Request) o;
            return name.equals(other.name);
        } else {
            return false;
        }
    }
}
