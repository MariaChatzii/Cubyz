#version 330 core

layout (location=0) in vec2 vertex_pos;
layout (location=1) in vec2 face_pos;

out vec2 frag_face_pos;


//in pixel
uniform vec4 texture_rect;
uniform vec2 scene;
uniform vec2 offset;
uniform float ratio;


vec2 convert2Proportional(vec2 original,vec2 full) {
	return vec2(original.x/full.x,original.y/full.y);
}


void main() {

	vec2 position_percentage 	= convert2Proportional(offset*ratio,scene);
	vec2 size_percentage		= convert2Proportional(vec2(texture_rect.z,texture_rect.w)*ratio,scene);
	
	//convert glyph coords to opengl coords
	vec4 rect = vec4(position_percentage,size_percentage);
	
	vec2 position = vec2(rect.x+vertex_pos.x*rect.z,-rect.y+vertex_pos.y*rect.w)*2+vec2(-1,1);
	
	gl_Position = vec4(position,0,1);
	frag_face_pos = face_pos;
}