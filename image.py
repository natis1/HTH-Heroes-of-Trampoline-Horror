'''

This should all work, but maybe it would be better with pycharm? Either way it'll run under python 3 with Pillow installed ( pip install Pillow )



from PIL import Image
from random import randint

def modifyField(x, variance):
    chance = randint(0,100)
    if chance > 50 : x += variance
    else : x -= variance
    if x < 0   : x = 0
    if x > 255 : x = 255
    return x

def genColor():
    return randint(0, 255), randint(0, 255), randint(0, 255), 255

def modifyColor(color, variance=1):
    modify = lambda i : modifyField(color[i], variance)
    return modify(0), modify(1), modify(2), modify(3)

def darken(color, amount=50):
    d = lambda i,n=amount : (color[i] - n) % 255 if color[i] > n else color[i]
    return d(0), d(1), d(2), color[3]

def lighten(color, amount=50): return darken(color, -amount)

def findComplementary(color):
    flip = lambda i : (color[i] + 127) % 255
    return flip(0), flip(1), flip(2), color[3]

def inBounds(bounds, i, size):
    y_pos = i // size
    x_pos = i % size
    if i > 0:
        if x_pos < bounds or x_pos > size - bounds or y_pos < bounds or y_pos > size - bounds:
            return False
        else:
            return True
    else:
        return False

def genData(size, restriction=6, variation=5):
    current_color = genColor()
    comp_color = findComplementary(current_color)
    data = []

    bounds = size // restriction
    variate = lambda c : modifyColor(c, variation)
    i = size * size
    while i > 0:
        i -= 1
        if inBounds(bounds, i, size):
            data.append(variate(current_color))
        else:
            data.append(variate(comp_color))
    return data

pixels = 256

img = Image.new('RGBA', (pixels, pixels))
img.putdata(genData(pixels))
img.save('image.png')

'''
