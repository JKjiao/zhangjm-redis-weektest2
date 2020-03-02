package com.zhangjm.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zhangjm.pojo.User;
import com.zhangjm.util.DateUtil;
import com.zhangjm.util.StringUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:redis.xml")
public class ExistTest {

	@Autowired
	RedisTemplate redisTemplate;

	@Autowired
	StringRedisSerializer stringRedisSerializer;

	@Autowired
	JdkSerializationRedisSerializer jdkSerializationRedisSerializer;

	/**
	 * JSON方式，存储数据进Redis
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void test01() {
		List<User> list = new ArrayList<User>();
		// 循环100000个user对象并添加进list
		for (int i = 0; i < 100000; i++) {
			String name = StringUtil.randomChineseNameForThreeChar();
			String gender = StringUtil.getRandomGender();
			String tel = StringUtil.getRandomPhone();
			String email = StringUtil.getRandomEmail();
			// 随机1949-2001生日的出生日期
			Date date1 = DateUtil.parse("1949-01-01 00:00:00", "yyyy-MM-dd HH:mm:ss");
			Date date2 = DateUtil.parse("2001-12-31 00:00:00", "yyyy-MM-dd HH:mm:ss");
			Date date = DateUtil.getMiddleTime(date1, date2);
			String birth = date.toLocaleString();
			User user = new User(i + 1, name, gender, tel, email, birth);
			list.add(user);
		}
		// 开始时间
		long startTime = System.currentTimeMillis();

		// 加入Redis
		System.out.println("序列化的方式是JDK");
		System.out.println("保存了十万条数据");
		Long pushAll = redisTemplate.opsForList().leftPushAll("user_list", list.toArray());

		// 结束时间
		long endTime = System.currentTimeMillis();

		System.out.println("将十万条User对象加入到Redis List中需要耗时为：" + (endTime - startTime));
	}

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Test public void test04() { List<User> list = new ArrayList<User>();
	 * //循环100000个user对象并添加进list for (int i = 0; i < 100000; i++) { User user = new
	 * User(); user.setId(i+1); user.setName("zhangsan"); user.setGender("男");
	 * user.setEmail("22166@qq.com"); user.setTel("137"); user.setBirth("2000-1-1");
	 * list.add(user); } //开始时间 long startTime = System.currentTimeMillis();
	 * 
	 * //加入Redis Long pushAll = redisTemplate.opsForList().leftPushAll("user_list2",
	 * list.toArray());
	 * 
	 * //结束时间 long endTime = System.currentTimeMillis();
	 * 
	 * System.out.println("将十万条User对象加入到Redis List中需要耗时为："+(endTime - startTime)); }
	 */
	
	
	
	/**
	 * HASH方式存储数据
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void test03() {
		HashMap<String, User> map = new HashMap<String, User>();
		// 循环100000个user对象并添加进list
		for (int i = 0; i < 100000; i++) {
			String name = StringUtil.randomChineseNameForThreeChar();
			String gender = StringUtil.getRandomGender();
			String tel = StringUtil.getRandomPhone();
			String email = StringUtil.getRandomEmail();
			// 随机1949-2001生日的出生日期
			Date date1 = DateUtil.parse("1949-01-01 00:00:00", "yyyy-MM-dd HH:mm:ss");
			Date date2 = DateUtil.parse("2001-12-31 00:00:00", "yyyy-MM-dd HH:mm:ss");
			Date date = DateUtil.getMiddleTime(date1, date2);
			String birth = date.toLocaleString();
			User user = new User(i + 1, name, gender, tel, email, birth);
			map.put(user.getName(),user);
		}
		// 开始时间
		long startTime = System.currentTimeMillis();

		// 加入Redis
		System.out.println("序列化的方式是HASH");
		System.out.println("保存了十万条数据");
		redisTemplate.opsForHash().putAll("user_map", map);

		// 结束时间
		long endTime = System.currentTimeMillis();
		System.out.println("将十万条User对象加入到Redis map中需要耗时为：" + (endTime - startTime));
	}

	
	
	
	@SuppressWarnings({ "static-access", "deprecation" })
	@Test
	public void test02() {
		Date date1 = DateUtil.parse("1949-01-01 00:00:00", "yyyy-MM-dd HH:mm:ss");
		Date date2 = DateUtil.parse("2001-12-31 00:00:00", "yyyy-MM-dd HH:mm:ss");
		Date date = DateUtil.getMiddleTime(date1, date2);
		String string2 = date.toLocaleString();

		Date birthday = DateUtil.getRandomDate(date1, date2);
		String localeString = birthday.toLocaleString();
		String string = birthday.toString();
		String bir = birthday.toString().format(birthday.toString(), "yyyy-MM-dd");
		System.out.println(string2);
	}
}
