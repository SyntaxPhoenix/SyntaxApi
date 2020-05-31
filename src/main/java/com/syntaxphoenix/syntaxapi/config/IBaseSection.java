package com.syntaxphoenix.syntaxapi.config;

import java.util.Map;
import java.util.Set;

import com.syntaxphoenix.syntaxapi.utils.config.SectionMap;

public interface IBaseSection {

	/**
	 * @return HashMap<String, Object> {section values}
	 */
	public Map<String, Object> getValues();
	
	/**
	 * @return Set<String> {section keys}
	 */
	public Set<String> getKeys();

	/**
	 * @return String {section name}
	 */
	public String getName();
	
	/**
	 * @return Boolean {name exists}
	 */
	public boolean isValid();
	
	/**
	 * 
	 */
	public void clear();
	
	/**
	 * @param String {path}
	 * @return Boolean {value exists}
	 */
	public boolean contains(String path);
	
	/**
	 * @param <E>
	 * @param String {path}
	 * @param Object {object instance}
	 * @return Boolean {value is instance}
	 */
	public <E> boolean isInstance(String path, E value);
	
	/**
	 * @param <E>
	 * @param String {path}
	 * @param Class {object instance}
	 * @return Boolean {value is instance}
	 */
	public <E> boolean isInstance(String path, Class<E> value);
	
	/**
	 * @param <E>
	 * @param String {path}
	 * @param Object {default value}
	 * @return Object {section value}
	 */
	public <E> E check(String path, E value);

	/**
	 * @param String {path}
	 * @return Object (Null) {section value}
	 */
	public Object get(String path);

	/**
	 * @param <E>
	 * @param String {path}
	 * @param Class<E> {sample value type}
	 * @return Object (Null) {section value}
	 */
	public <E> E get(String path, Class<E> sample);

	/**
	 * @param <E>
	 * @param String {path}
	 * @param E {sample value type}
	 * @return Object (Null) {section value}
	 */
	public <E> E get(String path, E sample);

	/**
	 * @param String {path}
	 * @return JsonSection (Null) {section value}
	 */
	public BaseSection getSection(String path);

	/**
	 * @param String {path}
	 * @return JsonSection (Null) {old / new section}
	 */
	public BaseSection createSection(String path);

	/**
	 * @param String {path}
	 * @param Object {value}
	 */
	public void set(String path, Object value);

	/**
	 * @param String[] {path}
	 * @param Object {value}
	 */
	public void set(String key, String[] path, Object value);
	
	/**
	 * @return SectionMap<String, Object> {config values}
	 */
	public SectionMap<String, Object> toMap();
	
	/**
	 * @param SectionMap<String, Object> {new config values}
	 */
	public void fromMap(SectionMap<String, Object> input);
}
