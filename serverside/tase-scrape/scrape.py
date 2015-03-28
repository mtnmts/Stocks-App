# Old, dontuse
from scrapy.contrib.linkextractors import LinkExtractor
from scrapy.contrib.spiders import CrawlSpider, Rule
import scrapy

BASE_DOMAIN = 							 r'www.tase.co.il' 
BASE_URL 	= r"http://" + BASE_DOMAIN + r'/Heb/MarketData/Securities/Pages/Securities.aspx'

# Financial security item
class Security(scrapy.Item):
	security_code 		= scrapy.Field()
	security_name 		= scrapy.Field()
	current_value_cent 	= scrapy.Field()


class SecuritySpider(CrawlSpider):
	name 			= "tase_scrap"
	allowed_domains = [BASE_DOMAIN]
	start_urls		= [BASE_URL]
	rules 			= [Rule(LinkExtractor(allow=['/Eng/General/Company/Pages/companyMainData.aspx.*']), 'parse_security')]
	
	def parse_security(self, response):
		security = Security()
		security['security_name'] 		= response.xpath(r'/html/body/meta[4]').extract()
		print "\nSECNAME:" + str(response.css(r'#\37').extract()) + '\n'
		security['security_code'] 		= response.xpath(r'//*[@id="divEtfDetails"]/table/tbody/tr[3]/td/table/tbody/tr/td[3]/table/tbody/tr[1]/td[3]').extract()
		security['current_value_cent'] 	= response.xpath(r'//*[@id="7"]').extract()		
		return security