package xm.bibibiradio.spider;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.log4j.Logger;

import xm.bibibiradio.util.LogFactory;
import xm.bibibiradio.util.GlobalConfig;

public class SpiderMain {
    static private final Logger LOGGER = LogFactory.provide(SpiderMain.class);

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Options opts = new Options();
        opts.addOption("help", false, "帮助");
        opts.addOption("url", true, "爬取根url");
        opts.addOption("policyMod", true, "爬取策略（stard）");
        opts.addOption("outputMod", true, "输出策略（httpfile）");
        opts.addOption("deep", true, "爬取深度");
        opts.addOption("basePath", true, "爬取文件输出目录");
        opts.addOption("getCssQuery", true, "获取数据的css selector（img[src]）");
        opts.addOption("scanCssQuery", true, "获取下一层url的css selector（a[href]）默认a[href]");
        opts.addOption("contentTag", true, "抓取数据特征（src）");
        opts.addOption("filterUrl", true, "url的过滤特征 默认空");
        opts.addOption("host", true, "白名单 默认空");
        opts.addOption("cookie", true, "设置Cookie 默认空");
        opts.addOption("ua", true, "设置ua 默认空");
        opts.addOption("interval", true, "发包间隔时间 ms 默认1000");
        opts.addOption("retry", true, "发包失败重试次数 默认3");
        opts.addOption("ctimeout", true, "连接超时 ms 默认10000");
        opts.addOption("stimeout", true, "socket超时时间 ms 默认30000");

        BasicParser parser = new BasicParser();
        CommandLine cl;

        try {
            cl = parser.parse(opts, args);
            if (cl.getOptions().length > 0) {
                if (cl.hasOption("help")) {
                    HelpFormatter hf = new HelpFormatter();
                    hf.printHelp("Options", opts);
                } else {
                    GlobalConfig.getConfig().getProp().put("url", cl.getOptionValue("url"));
                    GlobalConfig.getConfig().getProp()
                        .put("policyMod", cl.getOptionValue("policyMod"));
                    GlobalConfig.getConfig().getProp()
                        .put("outputMod", cl.getOptionValue("outputMod"));
                    GlobalConfig.getConfig().getProp().put("deep", cl.getOptionValue("deep"));
                    GlobalConfig.getConfig().getProp()
                        .put("basePath", cl.getOptionValue("basePath"));
                    GlobalConfig.getConfig().getProp()
                        .put("getCssQuery", cl.getOptionValue("getCssQuery"));
                    GlobalConfig
                        .getConfig()
                        .getProp()
                        .put(
                            "scanCssQuery",
                            cl.getOptionValue("scanCssQuery") != null ? cl
                                .getOptionValue("scanCssQuery") : "a[href]");
                    GlobalConfig.getConfig().getProp()
                        .put("contentTag", cl.getOptionValue("contentTag"));
                    GlobalConfig
                        .getConfig()
                        .getProp()
                        .put(
                            "filterUrl",
                            cl.getOptionValue("filterUrl") != null ? cl.getOptionValue("filterUrl")
                                : "");

                    GlobalConfig
                        .getConfig()
                        .getProp()
                        .put(
                            "host",
                            cl.getOptionValue("host") != null ? cl.getOptionValue("host")
                                : "");
                    GlobalConfig
                    .getConfig()
                    .getProp()
                    .put(
                        "cookie",
                        cl.getOptionValue("cookie") != null ? cl.getOptionValue("ua")
                            : "");
                    GlobalConfig
                    .getConfig()
                    .getProp()
                    .put(
                        "ua",
                        cl.getOptionValue("ua") != null ? cl.getOptionValue("ua")
                            : "");
                    GlobalConfig
                    .getConfig()
                    .getProp()
                    .put(
                        "interval",
                        cl.getOptionValue("interval") != null ? cl.getOptionValue("interval")
                            : "1000");
                    GlobalConfig
                    .getConfig()
                    .getProp()
                    .put(
                        "retry",
                        cl.getOptionValue("retry") != null ? cl.getOptionValue("retry")
                            : "3");
                    GlobalConfig
                    .getConfig()
                    .getProp()
                    .put(
                        "ctimeout",
                        cl.getOptionValue("ctimeout") != null ? cl.getOptionValue("ctimeout")
                            : "10000");
                    GlobalConfig
                    .getConfig()
                    .getProp()
                    .put(
                        "stimeout",
                        cl.getOptionValue("stimeout") != null ? cl.getOptionValue("stimeout")
                            : "30000");
                    //LOGGER.info(SpiderConfig.getConfig().getProp());

                    SpiderManager spider = new SpiderManager(GlobalConfig.getConfig().getProp());
                    spider.spider(GlobalConfig.getConfig().getProp().getProperty("url"));
                }
            }
        } catch (Exception ex) {
            LOGGER.error("error", ex);
        }
    }

}
