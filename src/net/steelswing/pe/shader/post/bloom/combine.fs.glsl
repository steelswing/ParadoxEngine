#version 120

varying vec2 textureCoords;

uniform sampler2D colorTexture;
uniform sampler2D highlightTexture;
uniform bool onWater;
uniform bool onBloom;

void main(void) {
    
    vec4 sceneColor = texture2D(colorTexture, textureCoords);
    vec4 highlightColor = texture2D(highlightTexture, textureCoords);

    if (onBloom) {
        gl_FragData[0] = sceneColor + highlightColor;
        gl_FragData[0].rgb = (gl_FragData[0].rgb - 0.5) * (1.0 + 0.5) + 0.6;
    } else {
        gl_FragData[0] = sceneColor;
    }
    if (onWater) {
        gl_FragData[0] = mix(gl_FragData[0], vec4(0, 0.5, 1, 1), 0.4);
    }

    gl_FragData[1] = gl_FragData[0];
}