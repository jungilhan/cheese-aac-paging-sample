# Cheese 
![Demo](https://raw.githubusercontent.com/jungilhan/cheese-aac-paging-sample/master/demo.gif)

이 샘플 앱은 아키텍처 컴포넌트의 페이징 라이브러리가 REST API와 어떻게 동작하는지를 보여줍니다. 또한 리사이클러뷰의 아이템 클릭 시, 페이징 라이브러리가 Observable<PagedList>를 통해 만료된 DataSource를 어떻게 다루는지도 확인할 수 있습니다.

# Client
안드로이드 앱은 PositionalDataSource로 REST API를 호출해 페이징 당 10개의 데이터 청크를 로드합니다. 또한 페이징 라이브러리의 1.0.0-beta1에 추가된 RxPagedListBuilder의 사용법과 Observable<PagedList>를 이용해 새로운 PaegdList와 DataSource의 pair가 어떻게 동작하는지 살펴 볼 수 있습니다. 

# Server
Flask 기반의 REST API 서버는 600여 개의 치즈 정보를 제공합니다. 오프셋 기반의 페이징으로 리스트의 일부분을 가져올 수 있고, '좋아요' 기능을 제공합니다. 

## 실행 방법
```
$ cd server
$ virtualenv venv
$ . venv/bin/activate
(venv) pip install -r requirements.txt
(venv) python app.py
```

## 치즈 정보 가져오기
```GET /cheeses.json?offset=5&limit=10```

```
{
  "cheeses": [
    {
      "description": "Acorn cheese is a hard, full fat cheese made entirely from Friesland sheep's milk by Karen and Don Ross at their Little Acorn farmhouse at Bethania in Ceredigion.",
      "id": 6,
      "like": 0,
      "name": "Acorn"
    },
    {
      "description": "Adelost is a Swedish blue cheese made from cow's milk.",
      "id": 7,
      "like": 0,
      "name": "Adelost"
    },
    {
      "description": "Given that Affidélice au Chablis is born in Burgundy, it bears a lot of similarity to Epoisses cheese.",
      "id": 8,
      "like": 0,
      "name": "Affidelice au Chablis"
    },

    ...
  ],
  "total": 650	
}
```

## '좋아요'하기
```PUT /cheeses/<int:cheese_id>/like.json```

```
{
  "description": "Abbaye de Belloc is a French Pyrenees, traditional farmhouse, semi-hard cheese from the Pays Basque region, made from unpasteurized sheep milk, with a fat content of 60%.",
  "id": 1,
  "like": 9,
  "name": "Abbaye de Belloc"
}
```
