package render;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL15.*;

public class Model {

    private int length;
    private int v_id;
    private int t_id;

    private float[] vertices;
    private float[] tex_coords;
    private FloatBuffer buffer;

    public Model (float[] vertices, float[] tex_coords){
        this.length = vertices.length / 2;
        this.vertices = vertices;
        this.tex_coords = tex_coords;
    }

    public void render() {
        updateVertexBuffer(vertices);
        updateTexBuffer(tex_coords);

        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_TEXTURE_COORD_ARRAY);

        glBindBuffer(GL_ARRAY_BUFFER, v_id);
        glVertexPointer(2, GL_FLOAT, 0, 0); //poop
        glBindBuffer(GL_ARRAY_BUFFER, t_id);
        glTexCoordPointer(2, GL_FLOAT, 0, 0);

        glDrawArrays(GL_TRIANGLES, 0, length);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glDisableClientState(GL_VERTEX_ARRAY);
        glDisableClientState(GL_TEXTURE_COORD_ARRAY);
    }

    private FloatBuffer createBuffer(float[] data){
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    private void updateVertexBuffer(float[] vertices){
        buffer = createBuffer(vertices);
        v_id = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, v_id);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    private void updateTexBuffer(float[] tex_coords){
        t_id = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, t_id);
        glBufferData(GL_ARRAY_BUFFER, createBuffer(tex_coords), GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public void setVertices(float[] vertices) {
        this.vertices = vertices;
    }
}
