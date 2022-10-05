import numpy as np
import matplotlib
matplotlib.use('TkAgg')
import matplotlib.pyplot as plt
from pulp import LpMaximize, LpProblem, LpStatus, lpSum, LpVariable


def show_point(model) -> None:
    x = [data_point.value() for data_point in model.variables()]
    print(f'x1 = {x[0]}\n' + f'x2 = {x[1]}')
    plt.scatter(x[0], x[1], color="red")


def show_info(model) -> None:
    print(f"status: {model.status}, {LpStatus[model.status]}")
    print(f"objective: {model.objective.value()}")

    for name, constraint in model.constraints.items():
        print(f"{name}: {constraint.value()}")


def create_model(*args: tuple):
    a, b, c, d, e, f, g, h, p, r, q = args
    model = LpProblem(name="small-problem", sense=LpMaximize)

    x_1 = LpVariable(name="x1", lowBound=0)
    x_2 = LpVariable(name="x2", lowBound=0, cat="Integer")

    model += (c * x_1 + d * x_2 <= e, "first_constraint")
    model += (f * x_1 + g * x_2 <= h, "second_constraint")
    model += (p * x_1 + r * x_2 <= q, "third_constraint")

    model += lpSum([a * x_1, b * x_2])

    status = model.solve()

    return model


def create_landscape(*args):
    a, b, c, d, e, f, g, h, p, r, q, x = args
    y2 = (e - c * x) / d
    y3 = (h - f * x) / g
    y4 = (q - p * x) / r

    y = [y2, y3, y4]

    for y_variable in y:
        plt.plot(x, y_variable)
        plt.plot(x, y_variable)
        plt.plot(x, y_variable)

    return y


def work(args: tuple):
    a, b, c, d, e, f, g, h, p, r, q = args
    x = np.linspace(0, a, 2000)

    y = create_landscape(*args, x)

    plt.xlim((0, a))
    plt.xlim((0, a))
    y5 = np.minimum(y[0], y[2])
    y6 = y[1]

    plt.fill_between(x, y5, y6, where=y5 > y6, color='grey', alpha=0.5)

    model = create_model(*args)

    show_point(model)
    show_info(model)

    plt.grid()
    plt.show()


if __name__ == '__main__':
    coeffs = (6, 5, 6, -3, 4, -6, 7, 5, 2, 2, 4)
    work(coeffs)
