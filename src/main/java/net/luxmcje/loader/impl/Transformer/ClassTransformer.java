package net.luxmcje.loader.impl.transformer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

public class ClassTransformer {

    public byte[] transform(String className, byte[] basicClass) {
        if (className.equals("net.minecraft.client.gui.screen.TitleScreen")) {
            return injectCustomCode(basicClass);
        }
        return basicClass;
    }

    private byte[] injectCustomCode(byte[] basicClass) {
        ClassReader reader = new ClassReader(basicClass);
        ClassNode classNode = new ClassNode();
        reader.accept(classNode, 0);

        for (MethodNode method : classNode.methods) {
            if (method.name.equals("init") || method.name.equals("method_25426")) {
                System.out.println("[Lux-Transformer] Injecting code into TitleScreen!");
            }
        }

        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        classNode.accept(writer);
        return writer.toByteArray();
    }
}
