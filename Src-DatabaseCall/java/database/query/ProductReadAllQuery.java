package com.example.database.query;

import com.example.database.dao.ProductDAO;
import com.example.database.data.Data;
import com.example.database.model.ProductModel;

import java.sql.SQLException;
import java.util.List;


public class ProductReadAllQuery extends Query
{
	private long mSkip = -1l;
	private long mTake = -1l;


	public ProductReadAllQuery()
	{
	}


	public ProductReadAllQuery(long skip, long take)
	{
		mSkip = skip;
		mTake = take;
	}


	@Override
	public Data<List<ProductModel>> processData() throws SQLException
	{
		Data<List<ProductModel>> data = new Data<List<ProductModel>>();
		data.setDataObject(ProductDAO.readAll(mSkip, mTake));
		return data;
	}
}
