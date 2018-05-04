# Cheese 
![Demo](https://raw.githubusercontent.com/jungilhan/cheese-aac-paging-sample/master/demo.gif)

아키텍처 컴포넌트의 페이징 라이브러리와 REST API를 이용해 페이징을 다루는 샘플 앱입니다. ['Paging Library, 그것이 쓰고싶다'](https://medium.com/@jungil.han/paging-library-%EA%B7%B8%EA%B2%83%EC%9D%B4-%EC%93%B0%EA%B3%A0%EC%8B%B6%EB%8B%A4-bc2ab4d27b87)에서 페이징 라이브러리에 대한 자세한 내용을 확인할 수 있습니다.

페이징 라이브러리가 제공하는 DataSource 예제를 브랜치 별로 살펴볼 수 있습니다. 
* PositionalDataSource - [master](https://github.com/jungilhan/cheese-aac-paging-sample/tree/master)
* ItemKeyedDataSource - [item_based_paging](https://github.com/jungilhan/cheese-aac-paging-sample/tree/item_based_paging)

# Client
스크롤 시 페이징 당 10개의 데이터 청크를 로드하고, 클릭 시 /:id/like.json API를 통해 '좋아요'수가 증가합니다. 이는 데이터 소스가 업데이트 됐을 때, RxPagedListBuilder를 통해 생성된 Observable<PagedList>가 새로운 PaegdList와 DataSource의 pair가 어떻게 동작하는지 살펴 볼 수 있습니다. 

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
