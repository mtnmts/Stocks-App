from flask import Flask
from flask.ext import restful
from scrape2 import *

app = Flask(__name__)
api = restful.Api(app)
stock_data = None
stock_data_map = {}


class StockData(restful.Resource):

    def get(self, stock_id):
        print "Attempting to resolve " + stock_id 
        if stock_id in stock_data_map:
            stock = stock_data_map[stock_id]
            return {'stock_name': stock[0],
                    'ticker': stock[1],'isin' : stock[2] ,'price': stock[4]}
        return {'stock_name': "ERROR", 'ticker': "ERROR", 'isin': "ERROR", 'price': 'N/A'}

api.add_resource(StockData, '/query_android/<string:stock_id>')

if __name__ == '__main__':
    stock_data = fetch_items_from_tase()
    for stock in stock_data:
        stock_data_map[str(stock[2])[4:-1]] = stock
    app.run(host='0.0.0.0',debug=True)
