package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Post;

public class PostDAOImpl implements PostDAO {

	private static Connection con;

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver loaded");

			con = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "blackthorne");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int insert(Post post) {
		int res = -1;
		try {
			PreparedStatement pst = con.prepareStatement("insert into posts (title,body) values(?,?)");
			pst.setString(1, post.getTitle());
			pst.setString(2, post.getBody());
			res = pst.executeUpdate();
			if(res > 0) {
				PreparedStatement pst1 = con.prepareStatement("select id from posts where title = ? and body = ?");
				pst1.setString(1, post.getTitle());
				pst1.setString(2, post.getBody());
				ResultSet rs = pst1.executeQuery();
				if(rs.next()) {
					post.setId(rs.getInt(1));
				}
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return post.getId();
	}

	@Override
	public int update(Post post) {
		int res = -1;
		try {
			PreparedStatement pst = con.prepareStatement("update posts set title = ?, body = ? where id = ?");
			pst.setString(1, post.getTitle());
			pst.setString(2, post.getBody());
			pst.setInt(3, post.getId());
			res = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public int delete(int id) {
		int res = -1;
		try {
			PreparedStatement pst = con.prepareStatement("delete from posts where id = ?");
			pst.setInt(1, id);
			res = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public List<Post> view() {

		ArrayList<Post> list = new ArrayList<>();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from posts");
			while (rs.next()) {
				list.add(new Post(rs.getInt(1), rs.getString(2), rs.getString(3)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public List<Post> view(String title) {

		ArrayList<Post> list = new ArrayList<>();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from posts where title like '%" + title + "%'");
			while (rs.next()) {
				list.add(new Post(rs.getInt(1), rs.getString(2), rs.getString(3)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public Post view(int id) {
		Post post = new Post();
		try {
			PreparedStatement pst = con.prepareStatement("select * from posts where id = ?");
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				post = new Post(rs.getInt(1), rs.getString(2), rs.getString(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return post;
	}

}
