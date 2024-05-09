package com.witelokk.hrapp;

public class Error {
    public static class Network extends Error {}
    public static class Unauthorized extends Error {}
    public static class Unknown extends Error {}
    public static class InvalidCredentials extends Error {}
}
