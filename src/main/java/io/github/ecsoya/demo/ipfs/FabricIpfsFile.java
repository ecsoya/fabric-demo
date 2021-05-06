package io.github.ecsoya.demo.ipfs;

import java.util.Date;

import io.github.ecsoya.fabric.annotation.FabricJson;
import io.github.ecsoya.fabric.bean.IFabricObject;

/**
 * 
 * File object on fabric and stored by using IPFS
 * 
 * @author ecsoya
 *
 */
@FabricJson
public class FabricIpfsFile implements IFabricObject {

	public static final String TYPE = "ipfs";

	private String id;

	protected String type = TYPE;

	/**
	 * The name of file
	 */
	private String name;

	/**
	 * The contentType of file
	 */
	private String contentType;

	/**
	 * The owner of file
	 */
	private String owner;

	/**
	 * The length of file
	 */
	private Long length;

	/**
	 * The time of creation
	 */
	private Date createTime;

	/**
	 * IPFS Hash
	 */
	private String hash;

	/**
	 * IPFS preview URL
	 */
	private String url;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Long getLength() {
		return length;
	}

	public void setLength(Long length) {
		this.length = length;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

}
