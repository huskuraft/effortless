package dev.huskuraft.effortless.networking.serializer;

import dev.huskuraft.effortless.api.math.Vector3i;
import dev.huskuraft.effortless.api.networking.NetByteBuf;
import dev.huskuraft.effortless.api.networking.NetByteBufSerializer;

public class Vector3iSerializer implements NetByteBufSerializer<Vector3i> {

    @Override
    public Vector3i read(NetByteBuf byteBuf) {
        return Vector3i.at(
                byteBuf.readInt(),
                byteBuf.readInt(),
                byteBuf.readInt()
        );
    }


    @Override
    public void write(NetByteBuf byteBuf, Vector3i vector) {
        byteBuf.writeInt(vector.x());
        byteBuf.writeInt(vector.y());
        byteBuf.writeInt(vector.z());
    }

}
