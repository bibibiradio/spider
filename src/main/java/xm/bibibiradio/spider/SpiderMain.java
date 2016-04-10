package xm.bibibiradio.spider;

import java.net.URL;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.log4j.Logger;

import xm.bibibiradio.util.LogFactory;
import xm.bibibiradio.util.SpiderConfig;

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
        opts.addOption("scanCssQuery", true, "获取下一层url的css selector（a[href]）可空");
        opts.addOption("contentTag", true, "抓取数据特征（src）");
        opts.addOption("filterUrl", true, "url的过滤特征 可空");
        opts.addOption("host", true, "白名单 可空");
        opts.addOption("cookie", true, "设置Cookie 可空");
        opts.addOption("ua", true, "设置ua 可空");

        BasicParser parser = new BasicParser();
        CommandLine cl;

        try {
            cl = parser.parse(opts, args);
            if (cl.getOptions().length > 0) {
                if (cl.hasOption("help")) {
                    HelpFormatter hf = new HelpFormatter();
                    hf.printHelp("Options", opts);
                } else {
                    SpiderConfig.getConfig().getProp().put("url", cl.getOptionValue("url"));
                    SpiderConfig.getConfig().getProp()
                        .put("policyMod", cl.getOptionValue("policyMod"));
                    SpiderConfig.getConfig().getProp()
                        .put("outputMod", cl.getOptionValue("outputMod"));
                    SpiderConfig.getConfig().getProp().put("deep", cl.getOptionValue("deep"));
                    SpiderConfig.getConfig().getProp()
                        .put("basePath", cl.getOptionValue("basePath"));
                    SpiderConfig.getConfig().getProp()
                        .put("getCssQuery", cl.getOptionValue("getCssQuery"));
                    SpiderConfig
                        .getConfig()
                        .getProp()
                        .put(
                            "scanCssQuery",
                            cl.getOptionValue("scanCssQuery") != null ? cl
                                .getOptionValue("scanCssQuery") : "a[href]");
                    SpiderConfig.getConfig().getProp()
                        .put("contentTag", cl.getOptionValue("contentTag"));
                    SpiderConfig
                        .getConfig()
                        .getProp()
                        .put(
                            "filterUrl",
                            cl.getOptionValue("filterUrl") != null ? cl.getOptionValue("filterUrl")
                                : "");

                    SpiderConfig
                        .getConfig()
                        .getProp()
                        .put(
                            "host",
                            cl.getOptionValue("host") != null ? cl.getOptionValue("host")
                                : "");
                    SpiderConfig
                    .getConfig()
                    .getProp()
                    .put(
                        "cookie",
                        cl.getOptionValue("cookie") != null ? cl.getOptionValue("ua")
                            : "");
                    SpiderConfig
                    .getConfig()
                    .getProp()
                    .put(
                        "ua",
                        cl.getOptionValue("ua") != null ? cl.getOptionValue("ua")
                            : "");
                    //LOGGER.info(SpiderConfig.getConfig().getProp());

                    SpiderManager spider = new SpiderManager();
                    spider.spider(SpiderConfig.getConfig().getProp().getProperty("url"));
                }
            }
        } catch (Exception ex) {
            LOGGER.error("error", ex);
        }
    }

}
