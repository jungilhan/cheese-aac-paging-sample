from flask import Flask, request
from flask_restful import abort, Resource, Api
from cheeses import CHEESES, find

app = Flask(__name__)
api = Api(app)

class Cheese(Resource):
    def get(self, cheese_id):
        abort_if_cheese_doesnt_exist(cheese_id)
        return find(cheese_id)

    def delete(self, cheese_id):
        abort_if_cheese_doesnt_exist(cheese_id)
        CHEESES.remove(find(cheese_id))
        return '', 204

class CheeseList(Resource):
    def get(self):
        args = request.args
        if len(args) == 0:
            abort(400, message="Query strings named offset and limit must not be empty.")

        start = int(args['offset'])
        end = min(start + int(args['limit']), len(CHEESES))
        return {'cheeses' : CHEESES[start:end], 'total': len(CHEESES) }

class LikingCheese(Resource):
    def put(self, cheese_id):
        abort_if_cheese_doesnt_exist(cheese_id)
        cheese = find(cheese_id)
        cheese['like'] += 1
        return cheese, 201

class CountingCheese(Resource):
    def get(self):
        return len(CHEESES)

@app.before_first_request
def before_first_request():
    for index, cheese in enumerate(CHEESES):
        cheese['id'] = index + 1
        cheese['like'] = 0

def abort_if_cheese_doesnt_exist(id):
    if not any(cheese['id'] == id for cheese in CHEESES):
        abort(404, message="Cheese {} doesn't exist".format(id))

api.add_resource(Cheese, '/cheeses/<int:cheese_id>.json', methods=['GET', 'DELETE'])
api.add_resource(CheeseList, '/cheeses.json', methods=['GET'])
api.add_resource(LikingCheese, '/cheeses/<int:cheese_id>/like.json', methods=['PUT'])
api.add_resource(CountingCheese, '/count.json', methods=['GET'])

if __name__ == '__main__':
    app.run(debug=True)