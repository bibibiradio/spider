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
        opts.addOption("policyMod", true, "爬取策略链以逗号分隔（std,normal）");
        opts.addOption("outputMod", true, "输出策略（httpfile）");
        opts.addOption(
            "policyConf",
            true,
            "爬取策略链配置以start开头，end结尾，链以逗号分隔，项以:分隔。为start:爬取cssQuery:爬取contentTag|分隔:输出cssQuery:输出contentTag|分隔:输出过滤器url白名单:输出过滤器url黑名单:输出过滤器html白名单:输出过滤器html黑名单:爬取过滤器url白名单:爬取过滤器url黑名单:爬取过滤器html白名单:爬取过滤器html黑名单");
        opts.addOption("deep", true, "爬取深度");
        opts.addOption("basePath", true, "爬取文件输出目录");
        opts.addOption("cookie", true, "设置Cookie 默认空");
        opts.addOption("ua", true, "设置ua 默认空");
        opts.addOption("interval", true, "发包间隔时间 ms 默认1000");
        opts.addOption("retry", true, "发包失败重试次数 默认3");
        opts.addOption("ctimeout", true, "连接超时 ms 默认10000");
        opts.addOption("stimeout", true, "socket超时时间 ms 默认30000");
        opts.addOption("output", true, "file mod输出");
        opts.addOption("thread", true, "输出线程数 默认3");

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
                    GlobalConfig.getConfig().getProp()
                        .put("policyConf", cl.getOptionValue("policyConf"));
                    GlobalConfig.getConfig().getProp().put("deep", cl.getOptionValue("deep"));
                    GlobalConfig.getConfig().getProp()
                        .put("basePath", cl.getOptionValue("basePath"));
                    GlobalConfig
                        .getConfig()
                        .getProp()
                        .put("cookie",
                            cl.getOptionValue("cookie") != null ? cl.getOptionValue("cookie") : "");
                    GlobalConfig
                        .getConfig()
                        .getProp()
                        .put("output",
                            cl.getOptionValue("output") != null ? cl.getOptionValue("output") : "");
                    GlobalConfig.getConfig().getProp()
                        .put("ua", cl.getOptionValue("ua") != null ? cl.getOptionValue("ua") : "");
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
                        .put("retry",
                            cl.getOptionValue("retry") != null ? cl.getOptionValue("retry") : "3");
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
                    GlobalConfig
                        .getConfig()
                        .getProp()
                        .put(
                            "thread",
                            cl.getOptionValue("thread") != null ? cl.getOptionValue("stimeout")
                                : "3");
                    LOGGER.info("Config: " + GlobalConfig.getConfig().getProp());

                    SpiderManager spider = new SpiderManager(GlobalConfig.getConfig().getProp());
                    spider.spider(GlobalConfig.getConfig().getProp().getProperty("url"));
                }
            }
        } catch (Exception ex) {
            LOGGER.error("error", ex);
        }
    }

}
