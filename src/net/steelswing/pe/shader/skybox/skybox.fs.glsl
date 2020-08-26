#version 120

varying vec3 pos;
varying vec4 out_color;

uniform samplerCube cubeMap;
uniform vec3 fogColor;

const float lowerLimit = 0.0;
const float upperLimit =  20.0;
    
// 96,107,165 //196, 128, 73 vec4(0.76, 0.50, 0.28, 1.0);
const vec4 color1 = vec4(0.37, 0.41, 0.64, 1.0);
const vec4 color2 = vec4(1, 1, 1, 1.0);

float smoothlyStep(float edge0, float edge1, float x){
    float t = clamp((x - edge0) / (edge1 - edge0), 0.0, 1.0);
    return t * t * (3.0 - 2.0 * t);
}
void main() {
    float fadeFactor = 1.0 - smoothlyStep(-50.0, 50.0, pos.y);
    vec4 finalColor = mix(mix(color1 - 0.1, color2, fadeFactor), color1 - 0.0, fadeFactor);//textureCube(cubeMap, pos);
    float factor = clamp((pos.y - lowerLimit) / (upperLimit - lowerLimit), 0.0, 1.0);
    
    finalColor = mix(finalColor, vec4(0, 0, 0, 1), 0.3);
    
    gl_FragData[0] = finalColor;//mix(vec4(fogColor, 0.4), finalColor, factor / 2);
    gl_FragData[1] = finalColor;
}