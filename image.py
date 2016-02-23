'''
from noise import pnoise2, snoise2
from PIL   import Image
from random import randint

img_size = 256
# img_size = int(input("Enter the size of the image:"))


def gen_color():
    return randint(0, 255), randint(0, 255), randint(0, 255), 255


def color_data(color, bmap):
    avg = lambda c, g: ((c[0] + g[0]) // 2, (c[1] + g[1]) // 2, (c[2] + g[2]) // 2, g[3])
    for i in range(len(bmap)):
        bmap[i] = avg(color, bmap[i])


def gen():
    for i in range(1, 8):
        octaves = i
        freq = 16.0 * octaves
        data = []
        for y in range(img_size):
            for x in range(img_size):
                v = int(snoise2(x / freq, y / freq, octaves) * 127.0 + 128.0)
                data.append((v, v, v, 255))

        color_data(gen_color(), data)
        print(data)

        img = Image.new('RGBA', (img_size, img_size))
        img.putdata(data)
        img.save("image_" + str(i) + ".png")

gen()
'''