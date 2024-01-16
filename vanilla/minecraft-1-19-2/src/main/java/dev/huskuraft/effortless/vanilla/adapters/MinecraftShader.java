package dev.huskuraft.effortless.vanilla.adapters;

import dev.huskuraft.effortless.api.renderer.Shader;
import dev.huskuraft.effortless.api.renderer.VertexFormat;

@FunctionalInterface
public interface MinecraftShader extends Shader {

    net.minecraft.client.renderer.ShaderInstance referenceValue();

    @Override
    default String getResource() {
        return referenceValue().getName();
    }

    default VertexFormat getVertexFormat() {
        return () -> referenceValue().getVertexFormat();
    }

    default MinecraftUniform getUniform(String param) {
        var uniform = referenceValue().getUniform(param);
        return uniform == null ? null : () -> uniform;
    }
}
