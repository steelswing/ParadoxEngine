#version 120

varying vec2 textureCoords;

uniform sampler2D colorTexture;

void main(void){
    vec4 color = texture2D(colorTexture, textureCoords);
    float brightness = (color.r * 0.1126) + (color.g * 0.3152) + (color.b * 0.0722);
    gl_FragData[0] = color * brightness;

   /* if (brightness > 7.0) {
        gl_FragData[0] = color;
    } else {
        gl_FragData[0] = vec4(0);
    }*/ 
    gl_FragData[1] = gl_FragData[0];
    
}