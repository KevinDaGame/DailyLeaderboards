package com.github.kevindagame;

public enum Permission {
    MANAGE("dailyleaderboards.manage"),
    HELP("dailyleaderboards.help"),
    STOP("dailyleaderboards.manage.stop"),
    START("dailyleaderboards.manage.start"),
    NEXT("dailyleaderboards.manage.next"),
    DEBUG("dailyleaderboards.debug"),
    STATUS("dailyleaderboards.status")
    ;

    private final String permission;
    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
