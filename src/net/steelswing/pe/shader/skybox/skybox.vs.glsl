#version 120

attribute vec3 position;
varying vec3 pos;
varying float pass_height;

uniform mat4 projMatrix;
uniform mat4 viewMatrix;

void main() {
	
    pos = position;
    gl_Position = projMatrix * viewMatrix * vec4(position, 1.0); 
    pass_height = position.y;
}