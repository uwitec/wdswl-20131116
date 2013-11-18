package nc.ui.zmpub.pub.report;

import java.util.Calendar;
import java.util.Hashtable;

/**
 * �������ڣ�(2004-7-28 13:42:29)
 * 
 * @author�������� UI�˼򵥵����ܼ��ӹ���. ��Ҫ������ָ��:��Ӧʱ��,�ڴ����.
 */
public class SamplePerfMonitor {
	private static SamplePerfMonitor m_monitor = null;
	// ������Ŀ.
	private java.util.Hashtable m_hTestItem = new Hashtable();

	public class PerfContext {
		// ��ʼʱ��.
		private long bgtime = 0;
		// ��ʼ�ڴ�.
		private long bgmem = 0;
		// �ϴδ�ӡʱ��.
		private long lasttime = 0;
		// �ϴδ�ӡ�ڴ�.
		private long lastmem = 0;

		public void setLastTime(long l) {
			lasttime = l;
		}

		public long getLastTime() {
			return lasttime;
		}

		public void setLastMem(long l) {
			lastmem = l;
		}

		public long getLastMem() {
			return lastmem;
		}

		// ��Ŀ����.
		private String itemname;
		// ��Ŀ����.
		private String itemKey;

		/**
		 * PerfContext ������ע�⡣
		 */
		public PerfContext() {
			super();
		}

		public long getBgMem() {
			return bgmem;
		}

		public long getBgTime() {
			return bgtime;
		}

		public String getItemKey() {
			return itemKey;
		}

		public String getItemName() {
			return itemname;
		}

		public void setBgMem(long l) {
			bgmem = l;
		}

		public void setBgTime(long l) {
			bgtime = l;
		}

		public void setItemKey(String key) {
			itemKey = key;
		}

		public void setItemname(String name) {
			itemname = name;
		}
	}

	/**
	 * SampleUIPerfMonitor ������ע�⡣
	 */
	private SamplePerfMonitor() {
		super();
	}

	private PerfContext getContextByKey(String key) {
		Object o = m_hTestItem.get(key);
		if (o == null)
			return null;
		return (PerfContext) o;

	}

	private String getCurUFtime() {
		nc.vo.pub.lang.UFDateTime time = new nc.vo.pub.lang.UFDateTime(Calendar
				.getInstance().getTime());
		return time.toString();
	}

	public static synchronized SamplePerfMonitor getInstance() {
		if (m_monitor == null)
			m_monitor = new SamplePerfMonitor();
		return m_monitor;
	}

	private Runtime getRuntime() {
		return Runtime.getRuntime();
	}

	/**
	 * ��ӡ��������.
	 **/
	public void printCurData(String key, String remark) {
		PerfContext context = getContextByKey(key);
		if (context == null)
			return;
		long curtime = System.currentTimeMillis();
		long costtime = curtime - context.getBgTime();
		long diffcosttime = curtime - context.getLastTime();
		long curmem = getRuntime().totalMemory() - getRuntime().freeMemory();
		long costmem = curmem - context.getBgMem();
		long diffmem = curmem - context.getLastMem();
		context.setLastMem(curmem);
		context.setLastTime(curtime);
		remark = (remark == null ? "" : "---" + remark);
		System.out.println("#####������Ŀ:" + context.getItemName() + remark);
		System.out.println("#####���ϴδ�ӡ�ȽϺ�ʱ	 :" + Long.toString(diffcosttime)
				+ "����.");
		System.out.println("#####���ϴδ�ӡ�ȽϺ��ڴ�   :" + (float) diffmem / 1024
				+ "K.");
		System.out.println("#####�ܺ�ʱ	 :" + Long.toString(costtime) + "����.");
		System.out.println("#####�ܺ��ڴ�   :" + (float) costmem / 1024 + "K.");
	}

	/**
	 *ע��һ��������Ŀ.
	 */
	public void registItem(String key, String itemname) {
		PerfContext context = new PerfContext();
		context.setBgTime(System.currentTimeMillis());
		context
				.setBgMem(getRuntime().totalMemory()
						- getRuntime().freeMemory());
		context.setLastMem(getRuntime().totalMemory()
				- getRuntime().freeMemory());
		context.setLastTime(System.currentTimeMillis());
		context.setItemKey(key);
		context.setItemname(itemname);
		m_hTestItem.put(key, context);
		System.out.println("#####������Ŀ:" + itemname + "\n######��ʼ����ʱ��:"
				+ getCurUFtime());
	}

	/**
	 * ����������Ŀ.
	 */
	public void stopTest(String key) {
		PerfContext context = getContextByKey(key);
		if (context == null)
			return;
		System.out.println("#####������Ŀ:" + context.getItemName() + "��������.");
		m_hTestItem.remove(key);
	}
}
