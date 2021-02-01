precision highp float;

attribute vec4 a_Position;
attribute vec2 a_TextureCoordinates;

uniform float u_Width;
uniform float u_Height;

uniform mat4 u_Matrix;
uniform float u_isDynamicAlpha;

varying vec2 v_TextureCoordinates;
varying float v_RandAlpha;

float rand(vec2 co){
    return fract(sin(dot(co.xy, vec2(12.9898, 78.233))) * 43758.5453);
}

void main()
{
    v_TextureCoordinates = a_TextureCoordinates;

    gl_PointSize = 50.0;//float

    float px = 1.0 * a_Position.x * 2.0 / u_Width;
    float py = -1.0 * float(int(a_Position.y)) * 2.0 / u_Height;

    v_RandAlpha = 1.0;

    if (u_isDynamicAlpha > 0.5)
    {
        float temp;
        if (px >= 0.6 && px < 0.7)
        {
            temp = 1.0;
        }
        else if (px >= 0.5 && px < 0.6)
        {
            temp = 0.2;
        }
        else if (px >= 0.4 && px < 0.5)
        {
            temp = 0.1;
        }
        else if (px >= 0.3 && px < 0.4)
        {
            temp = 0.5;
        }
        else if (px >= 0.2 && px < 0.3)
        {
            temp = 1.0;
        }
        else if (px >= 0.1 && px < 0.2)
        {
            temp = 0.5;
        }
        else if (px >= 0.0 && px < 0.1)
        {
            temp = 1.0;
        }
        v_RandAlpha = temp;
    }

    vec4 newPosition = vec4(px, py, 0.0, 1.0);

    gl_Position = u_Matrix * newPosition;

}   
