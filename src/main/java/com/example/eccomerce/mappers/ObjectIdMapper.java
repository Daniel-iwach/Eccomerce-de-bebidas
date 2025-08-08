package com.example.eccomerce.mappers;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
public class ObjectIdMapper {

    public String asString(ObjectId objectId) {
        return objectId != null ? objectId.toHexString() : null;
    }

    public ObjectId asObjectId(String id) {
        return (id != null && ObjectId.isValid(id)) ? new ObjectId(id) : null;
    }
}
