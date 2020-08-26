#version 120

attribute vec3 position;
varying vec3 pos;

uniform mat4 projMatrix;
uniform mat4 viewMatrix;
uniform mat4 tranMatrix;

void main() {
	
    //pos = position;
    //gl_Position = projMatrix * viewMatrix * tranMatrix * vec4(position, 1.0); 

    vec4 worldPosition = tranMatrix * vec4(position, 1.0);
    vec4 positionRealitiveToCam = viewMatrix * worldPosition;
    gl_Position = projMatrix * positionRealitiveToCam;
}