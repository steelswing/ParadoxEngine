#version 120

attribute vec3 position;
attribute vec2 uiCoords;
attribute vec3 normals;  
 
varying vec2 textureCoords;
varying vec3 surfaceNormal;
varying vec3 toLightVector[25];
varying vec3 toCameraVector;
varying float visibility;
varying vec4 shadowCoords;
 
uniform mat4 tranMatrix;
uniform mat4 projMatrix;
uniform mat4 viewMatrix;

uniform mat4 toShadowMapSpace;

uniform vec3 lightPosition[25];
uniform float useFakeLight;

uniform float numberOfRows;
uniform vec2 offset;
uniform vec4 plane;

uniform bool activeShadow;
uniform float shadowRenderDistance;
uniform float shadowTransitionDistance;

uniform int maxLights;
 
const float density = 0.00097; 
const float gradient = 1.8; 

uniform bool debug;
 
void main() {

    /*if (!debug) {
*/
        vec4 worldPosition = tranMatrix * vec4(position, 1.0);
        shadowCoords = toShadowMapSpace * worldPosition;


        vec4 positionRealitiveToCam = viewMatrix * worldPosition;
        gl_Position = projMatrix * positionRealitiveToCam;

        textureCoords = (uiCoords / numberOfRows) + offset;

        vec3 actualNormal = normals;
        if(useFakeLight > 0.5) {
            actualNormal = vec3(0.0, 1.0, 0.0);
        }

        surfaceNormal = (tranMatrix * vec4(actualNormal, 0.0)).xyz;
        for(int i = 0; i < 25; i++) {
            toLightVector[i] = lightPosition[i] - worldPosition.xyz;
        }
        toCameraVector = (viewMatrix * vec4(0.0, 0.0, 0.0, 1.0)).xyz - worldPosition.xyz;
        float distance = length(positionRealitiveToCam.xyz);
        visibility = clamp(exp(-pow((distance * density), gradient)), 0.0, 1.0);

        if (activeShadow) {
            distance = distance - (shadowRenderDistance - shadowTransitionDistance);
            distance = distance / shadowTransitionDistance;
            shadowCoords.w = clamp(1.0 - distance, 0.0, 1.0);
        }
 /*   } else {
        vec4 worldPosition = tranMatrix * vec4(position, 1.0);
        vec4 positionRealitiveToCam = viewMatrix * worldPosition;
        gl_Position = projMatrix * positionRealitiveToCam;
    }*/
}