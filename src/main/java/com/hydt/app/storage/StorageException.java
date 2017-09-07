package com.hydt.app.storage;

/**
 * Created by bean_huang on 2017/9/7.
 */
public class StorageException extends RuntimeException {

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
