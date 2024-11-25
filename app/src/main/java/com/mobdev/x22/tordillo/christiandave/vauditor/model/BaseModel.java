package com.mobdev.x22.tordillo.christiandave.vauditor.model;

import android.content.ContentValues;

public abstract class BaseModel {
    /**
     * Use this to generate a {@code ContentValues} object
     * <p>
     * <s>Used for updating or editing of a existing value into the database</s>
     * <p>
     * <font color="red"><b>No use case discovered; Might deprecate.</b></font>
     * @return Returns all values of the model as {@code ContentValues}
     */
    public abstract ContentValues generateContentValuesWithId();

    /**
     * Use this to generate a {@code ContentValues} object without an entry for _id
     * <p>
     * Used for inserting a new value into a database
     * <p>
     * <font color="yellow">WARNING: </font> <i>do not use when updating a transaction,
     * create a custom {@code ContentValues} object instead as updating multiple columns
     * may lead to a performance issue</i>
     * @return Returns all values of the model except for _id as {@code ContentValues}
     */
    public abstract ContentValues generateContentValuesWithoutId();
}
