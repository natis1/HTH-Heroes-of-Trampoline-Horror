from noise import pnoise2, snoise2
from PIL    import Image

img_size = 256
#img_size = int(input("Enter the size of the image:"))

octaves = 4
freq = 16.0 * octaves
data = []
for y in range(img_size):
    for x in range(img_size):
        v = int(snoise2(x / freq, y / freq, octaves) * 127.0 + 128.0)
        data.append((v, v, v, 255))

print(data)

img = Image.new('RGBA', (img_size, img_size))
img.putdata(data)
img.save("image.png")