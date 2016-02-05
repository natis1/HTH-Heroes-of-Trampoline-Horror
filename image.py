from PIL    import Image
import Perlin

img_size = int(input("Enter the size of the image:"))

def gen_Pmap(size):
    noise = Perlin.gen_Perlin(size)
    data = []
    for node in noise:
        data.append((node, node, node, 255))
    return data

img = Image.new('RGBA', (img_size, img_size))
img.putdata(gen_Pmap(img_size))
img.save("image.png")

# Here is a cloud nine project so you can see how it works (interpreter included)
# https://ide.c9.io/bananasky/image_creation 

# Here is an example of noise generated with this method: 
# http://i.imgur.com/4qOMeTs.png
