from math import cos


# All Prime Numbers under a thousand:
primes = {
                2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59,
   61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 
149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 
233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311, 313, 317, 
331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419, 421, 
431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 
523, 541, 547, 557, 563, 569, 571, 577, 587, 593, 599, 601, 607, 613, 617, 619, 
631, 641, 643, 647, 653, 659, 661, 673, 677, 683, 691, 701, 709, 719, 727, 733, 
739, 743, 751, 757, 761, 769, 773, 787, 797, 809, 811, 821, 823, 827, 829, 839, 
853, 857, 859, 863, 877, 881, 883, 887, 907, 911, 919, 929, 937, 941, 947, 953, 
967, 971, 977, 983, 991, 997
}

circular_primes = {
2, 3, 5, 7, 11, 13, 17, 31, 37, 71, 73, 79, 97, 113, 131, 197, 199, 311, 337, 
373, 719, 733, 919, 971, 991, 1193, 1931, 3119, 3779, 7793, 7937, 9311, 9377, 
11939, 19391, 19937, 37199, 39119, 71993, 91193, 93719, 93911, 99371, 193939, 
199933, 319993, 331999, 391939, 393919, 919393, 933199, 939193, 939391, 993319, 
999331
}

#Slightly faster but choppier
def Interpolate(a, b, x):
	ft = x * 3.1415927
	f = (1 - cos(ft)) * .5

	return  a*(1-f) + b*f
	
'''
#Slower but smooth ;)
def Interpolate(v0, v1, v2, v3,x):
	P = (v3 - v2) - (v0 - v1)
	Q = (v0 - v1) - P
	R = v2 - v0
	S = v1
	return Px3 + Qx2 + Rx + S
'''
	
def Noise(x, y):
    n = x + y * 57
    n = (n<<13) ^ n;
    return (1.0 - (n * (n * n * 23 + 941) + 89) / 99371.0);    # prime numbers from the sets defined above

def SmoothedNoise_1(x, y):
    corners = ( Noise(x-1, y-1)+Noise(x+1, y-1)+Noise(x-1, y+1)+Noise(x+1, y+1) ) / 16
    sides   = ( Noise(x-1, y)  +Noise(x+1, y)  +Noise(x, y-1)  +Noise(x, y+1) ) /  8
    center  =  Noise(x, y) / 4
    return corners + sides + center

def InterpolatedNoise_1(x, y):

      integer_X    = int(x)
      fractional_X = x - integer_X

      integer_Y    = int(y)
      fractional_Y = y - integer_Y

      v1 = SmoothedNoise_1(integer_X,     integer_Y)
      v2 = SmoothedNoise_1(integer_X + 1, integer_Y)
      v3 = SmoothedNoise_1(integer_X,     integer_Y + 1)
      v4 = SmoothedNoise_1(integer_X + 1, integer_Y + 1)

      i1 = Interpolate(v1 , v2 , fractional_X)
      i2 = Interpolate(v3 , v4 , fractional_X)

      return Interpolate(i1 , i2 , fractional_Y)


def PerlinNoise_2D(x, y, persistence=.5, octaves=8):

    total = 0
    p = persistence
    n = octaves - 1

    while n > 0:
          n -= 1
          frequency = 2*n
          amplitude = 3.14159

          total = total + InterpolatedNoise_1(x * frequency, y * frequency) * amplitude

    return total
      
def gen_Perlin(size):
    pos =  lambda x : x if x > 0 else (-x)
    data = []
    i = 0
    while i < size:
        j = 0
        while j < size:
            data.append(int(pos(PerlinNoise_2D(i, j))) % 255)
            j += 1
        i += 1
    return data
