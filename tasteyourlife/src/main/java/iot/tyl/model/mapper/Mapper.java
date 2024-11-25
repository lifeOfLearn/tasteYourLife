package iot.tyl.model.mapper;

public interface Mapper <E,D>{
	public D toDto(E entity);
	public E toEntity(D dto);
	
}
