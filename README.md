# Cheese 
This sample application shows you how the Paging Library works with a REST API.

![Demo](https://raw.githubusercontent.com/jungilhan/cheese-aac-paging-sample/master/demo.gif)

### Staring the Server
```
$ cd server
$ virtualenv venv
$ . venv/bin/activate
(venv) pip install -r requirements.txt
(venv) python app.py
```

### APIs
#### Get Cheeses
This server provides Id-based paging that supports bidirectional pagination.

```[GET] http://127.0.0.1:5000/cheeses.json?since=5&limit=2```

#### Parameters
Name | Type | Description
---- | ---- | -----------
since |	int | The integer ID of the last cheese that you've seen.
until |	int | The integer ID of the first cheese that you've seen.
around | int | The integer ID of the middle cheese that you've seen.
limit | int | The maximum number of cheeses that may be returned.

#### Response
```
{
  "cheeses": [
    {      
      "id": 6,
      "like": 3,
      "name": "Acorn"
      "description": "Acorn cheese is a hard, full fat cheese made entirely from Friesland sheep's milk by Karen and Don Ross at their Little Acorn farmhouse at Bethania in Ceredigion.",
    },
    {      
      "id": 7,
      "like": 5,
      "name": "Adelost"
      "description": "Adelost is a Swedish blue cheese made from cow's milk.",
    }
  ],
  "total": 650
}
```

#### Like a cheese
```PUT /cheeses/:id/like.json```

```
{  
  "id": 1,
  "like": 9,
  "name": "Abbaye de Belloc"
  "description": "Abbaye de Belloc is a French Pyrenees, traditional farmhouse, semi-hard cheese from the Pays Basque region, made from unpasteurized sheep milk, with a fat content of 60%.",
}
```
