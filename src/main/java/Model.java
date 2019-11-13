import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL15.*;

public class Model {

    private int length;
    private int v_id;

    public Model (float[] vertices){
        length = vertices.length / 2;

        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length);
        buffer.put(vertices);
        buffer.flip();

        v_id = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, v_id);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, v_id);
    }

    public void render() {
        glEnableClientState(GL_VERTEX_ARRAY);

        glBindBuffer(GL_ARRAY_BUFFER, v_id);
        glVertexPointer(2, GL_FLOAT, 0, 0);

        glDrawArrays(GL_TRIANGLES, 0, length);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glDisableClientState(GL_VERTEX_ARRAY);
    }
}
