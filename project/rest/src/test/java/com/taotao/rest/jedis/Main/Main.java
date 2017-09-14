package com.taotao.rest.jedis.Main;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for (int i = 0; i < 2; i ++) {
			try {
				int a = 1/0;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			} finally {
				System.out.println(i);
			}
			
		}
		
		for (int i = 0; i < 2; i ++) {
			try {
				int a = 1/0;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			}
			System.out.println(i);
			
		}
	}

}
