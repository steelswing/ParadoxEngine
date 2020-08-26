#version 120

varying vec2 textureCoords;

uniform sampler2D colourTexture;
//uniform sampler2D highlightTexture1;
//uniform sampler2D highlightTexture2;
//uniform sampler2D highlightTexture3;


const float contrast = 0.3;

void main(void){

    vec4 sceneColor = texture2D(colourTexture, textureCoords);
    
   // vec4 sceneColor1 = texture2D(highlightTexture1, textureCoords);
   // vec4 sceneColor2 = texture2D(highlightTexture2, textureCoords);
   // vec4 sceneColor3 = texture2D(highlightTexture3, textureCoords);

    gl_FragData[0] = sceneColor;// + sceneColor1 + sceneColor2 + sceneColor3;
    gl_FragData[1] = gl_FragData[0];
    //gl_FragData[0].rgb = (gl_FragData[0].rgb - 0.5) * (1.0 + contrast) + 0.5;
}