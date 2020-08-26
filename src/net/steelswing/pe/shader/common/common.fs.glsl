#version 120

varying vec2 textureCoords;

varying vec3 //
        surfaceNormal,
        toLightVector[25],
        toCameraVector;


varying float visibility;
varying vec4 shadowCoords;

uniform sampler2D 
        textureSampler,
        shadowMap,
        specularSampler;

uniform vec3 //
        lightColor[25],
        attenuation[25],
        skyColor,
        debugColor;

uniform float //
        shineDamper,
        reflectivity;

uniform bool //
        useSpecularMap,
        debug,
        activeShadow;
uniform int //
        pcfCount,
        shadowMapSize;

vec3[2] calculateLights(vec3 unitNormal, vec3 unitCameraVector) {
    
    vec3 finalDiffuse = vec3(0.0);
    vec3 finalSpecular = vec3(0.0);

    for(int i = 0; i < 25; i++) {
        float distance = length(toLightVector[i]);
        float attFactor = attenuation[i].x + (attenuation[i].y * distance) + (attenuation[i].z * distance * distance);

        vec3 unitLightVector = normalize(toLightVector[i]); 
        float brightness = max(dot(unitNormal, unitLightVector), 0.0);
        vec3 specularValue = pow(max(dot(reflect(-unitLightVector, unitNormal), unitCameraVector), 0.0), shineDamper) * reflectivity * lightColor[i];
       
        finalDiffuse = finalDiffuse + (brightness * lightColor[i]) / attFactor;
        finalSpecular = finalSpecular + specularValue / attFactor;
    }
    
    return vec3[] (finalDiffuse, finalSpecular);
}

float calculateShadows() {
    float lightFactor = 1;

    if (activeShadow) {
        int pcf = 2, mapSize = 512;

        if (pcfCount > 0) {
            pcf = pcfCount;
        }
        if(shadowMapSize > 0) {
            mapSize = shadowMapSize;
        }
 
        float totalTexels = (pcf * 2.0 + 1.0) * (pcf * 2.0 + 1.0);        
        float texelSize = 1.0 / mapSize;
        float total = 0.0;
        vec4 shadowCoords2 = vec4(shadowCoords.x , shadowCoords.y, shadowCoords.z, shadowCoords.w);
        shadowCoords2.z = shadowCoords2.z - texelSize;


        for(int x = -pcf; x <= pcf; x++) {
            for(int y = -pcf; y <= pcf; y++) {
                float objectNearestLight = texture2D(shadowMap, shadowCoords2.xy + vec2(x, y) * texelSize).r;
                if(shadowCoords2.z > objectNearestLight + 0.001) { 
                    total += 1;
                }
            }
        }
        total /= totalTexels;
        lightFactor = 1.0 - (total * shadowCoords2.w);
    }
    return lightFactor;
}

float smoothlyStep(float edge0, float edge1, float x){
    float t = clamp((x - edge0) / (edge1 - edge0), 0.0, 1.0);
    return t * t * (3.0 - 2.0 * t);
}

void main() {
    
    if (!debug) {
        float lightFactor = calculateShadows();    
        vec3[2] calculatedLight = calculateLights(normalize(surfaceNormal), normalize(toCameraVector));

        vec3 finalDiffuse = calculatedLight[0];
        vec3 finalSpecular = calculatedLight[1];



        finalDiffuse = mix(max(finalDiffuse, 0.5), vec3(lightFactor, lightFactor, lightFactor), 0.17);

        vec4 textureColor = texture2D(textureSampler, textureCoords);

        if(textureColor.a < 0.5) {
            //discard;
        }   

        gl_FragData[1] = vec4(0);
        if (useSpecularMap) {
            vec4 mapData = texture2D(specularSampler, textureCoords);
            finalSpecular *= mapData.r;

            if (mapData.b >= 0.2 && mapData.b <= 0.3) {
                finalDiffuse = vec3(1.0);
                gl_FragData[1] = (textureColor + vec4(finalSpecular, 1));
            }
            if (mapData.b >= 0.5 && mapData.b <= 0.9) {
                finalDiffuse = vec3(1.0);
                gl_FragData[1] = (textureColor + vec4(finalSpecular, 1) / 2);
            }
        }
            
        // GLSL 1.20 - gl_FragData[0] - OUT COLOR!
        

 
        gl_FragData[0] = mix(vec4(skyColor, 1.0), vec4(finalDiffuse, 1) * textureColor + vec4(finalSpecular, 1), visibility);
    } else {
        gl_FragData[0] = vec4(debugColor, 1);
    }
    gl_FragData[1] = gl_FragData[0];

    
}