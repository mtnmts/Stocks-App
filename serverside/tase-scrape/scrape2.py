import yahoo_finance as yf
import stock_list
from multiprocessing import Pool, freeze_support
import json

WORKER_COUNT = 40


def log(s):
    print "[*] " + s


def fetch_items_from_tase():
    csv = stock_list.get_stock_csv()
    data = stock_list.parse_stock_csv(csv)
    return data


def resolve_shares(items):
    items = [item for item in items if item[3] == 'Shares']
    log("Items to resolve: " + str(len(items)))
    resolved = []
    for item in items:
        resolved.append(lookup_stock(item[1]))


def lookup_stock(ticker):
    print ticker
    yh_ta_ticker = ticker.replace('.', '-') + '.TA'
    try:
        return yf.Share(yh_ta_ticker)
    except:
        pass
    return None


def resolve_all_async(items):
    pool = Pool(processes=WORKER_COUNT)
    tickers = [item[1] for item in items]
    result = pool.map(lookup_stock, tickers)
    return [r for r in result if r is not None]

if __name__ == "__main__":
    freeze_support()
    log("Obtaining stocks from TASE")
    z = fetch_items_from_tase()
    log("Stocks obtained")
    # res = resolve_all_async(z)
    # stock_data = json.dumps([r.data_set for r in res])
    # open('stock_data.json', 'wb').write(stock_data)
